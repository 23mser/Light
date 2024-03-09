package com.moskovets.light.requests;

import com.moskovets.light.exceptions.AuthException;
import com.moskovets.light.exceptions.IncorrectRequestException;
import com.moskovets.light.exceptions.RequestNotFoundException;
import com.moskovets.light.users.User;
import com.moskovets.light.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserService userService;

    @Transactional
    public RequestDto createRequestDraft(Long userId, String text) {
        User user = userService.findUserById(userId);

        Request request = new Request();
        request.setText(text);
        request.setCreated(LocalDateTime.now());
        request.setRequesterId(userId);
        request.setRequesterName(user.getUsername());
        request.setRequesterPhone(user.getPhone());
        request.setStatus(RequestStatus.DRAFT);
        Request savedRequest = requestRepository.save(request);

        return RequestMapper.toDto(savedRequest);
    }

    @Transactional
    public RequestDto sendRequest(Long userId, Long requestId) {
        userService.checkExistUserById(userId);

        Request request = requestRepository.findByIdAndRequesterId(requestId, userId).orElseThrow(() ->
                new RequestNotFoundException("Запрос не найден"));

        if (!request.getStatus().name().equals("DRAFT")) {
            throw new IncorrectRequestException("Запрос не может быть отправлен.");
        }

        request.setStatus(RequestStatus.SENT);
        Request savedRequest = requestRepository.save(request);
        return RequestMapper.toDto(savedRequest);
    }

    @Transactional
    public RequestDto updateRequestDraft(Long userId, Long requestId, String text) {
        userService.checkExistUserById(userId);

        Request request = requestRepository.findByIdAndRequesterId(requestId, userId).orElseThrow(() ->
                new RequestNotFoundException("Запрос не найден"));

        if (!request.getStatus().name().equals("DRAFT")) {
            throw new IncorrectRequestException("Запрос не может быть изменен.");
        }

        request.setText(text);
        request.setCreated(LocalDateTime.now());
        Request savedRequest = requestRepository.save(request);
        return RequestMapper.toDto(savedRequest);
    }

    public List<RequestDto> getSentRequests(Integer from, Integer size, String sort) {
        if (sort.equals("asc")) {
            Sort ascending = Sort.by("created").ascending();
            List<Request> requests = requestRepository.
                    findAll(PageRequest.of(from, size, ascending)).
                    stream().
                    filter(request -> request.getStatus().name().equals("SENT"))
                    .collect(Collectors.toList());
            return RequestMapper.toDtoList(requests);
        }
        Sort descending = Sort.by("created").descending();
        List<Request> requests = requestRepository.
                findAll(PageRequest.of(from, size, descending)).
                stream().
                filter(request -> request.getStatus().name().equals("SENT"))
                .collect(Collectors.toList());
        return RequestMapper.toDtoList(requests);
    }

    public List<RequestDto> getSentRequestsByUserId(Long userId, Integer from, Integer size, String sort) {
        userService.checkExistUserById(userId);
        if (sort.equals("asc")) {
            Sort ascending = Sort.by("created").ascending();
            List<Request> requests = requestRepository.
                    findAllByRequesterId(userId, PageRequest.of(from, size, ascending)).
                    stream().
                    filter(request -> request.getStatus().name().equals("SENT"))
                    .collect(Collectors.toList());
            return RequestMapper.toDtoList(requests);
        }
        Sort descending = Sort.by("created").descending();
        List<Request> requests = requestRepository.
                findAllByRequesterId(userId, PageRequest.of(from, size, descending)).
                stream().
                filter(request -> request.getStatus().name().equals("SENT"))
                .collect(Collectors.toList());
        return RequestMapper.toDtoList(requests);
    }

    public RequestDto getRequestById(Long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() ->
                new RequestNotFoundException("Запрос не найден"));
        if (!request.getStatus().name().equals("SENT")) {
            throw new AuthException("У вас нет доступа смотреть неотправленные заявки.");
        }
        return RequestMapper.toDto(request);
    }

    @Transactional
    public RequestDto confirmRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() ->
                new RequestNotFoundException("Запрос не найден"));

        if (!request.getStatus().name().equals("SENT")) {
            throw new IncorrectRequestException("Запрос не может быть подтвержден.");
        }
        request.setStatus(RequestStatus.CONFIRMED);
        Request savedRequest = requestRepository.save(request);
        return RequestMapper.toDto(savedRequest);
    }

    @Transactional
    public RequestDto rejectRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() ->
                new RequestNotFoundException("Запрос не найден"));

        if (!request.getStatus().name().equals("SENT")) {
            throw new IncorrectRequestException("Запрос не может быть отклонен.");
        }

        request.setStatus(RequestStatus.REJECTED);
        Request savedRequest = requestRepository.save(request);
        return RequestMapper.toDto(savedRequest);
    }

    public List<RequestDto> searchSentRequestsByUsername(String username, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        if (username == null || username.isBlank()) {
            return Collections.emptyList();
        }
        return requestRepository.searchRequestsByUsername(username, pageRequest)
                .stream()
                .filter(request -> request.getStatus().name().equals("SENT"))
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<RequestDto> getAllRequests(Integer from, Integer size, String sort) {
        if (sort.equals("asc")) {
            Sort ascending = Sort.by("created").ascending();
            List<Request> requests = requestRepository.findAll(PageRequest.of(from, size, ascending))
                    .stream()
                    .filter(request -> !request.getStatus().name().equals("DRAFT"))
                    .collect(Collectors.toList());
            return RequestMapper.toDtoList(requests);
        }
        Sort descending = Sort.by("created").descending();
        List<Request> requests = requestRepository.findAll(PageRequest.of(from, size, descending))
                .stream()
                .filter(request -> !request.getStatus().name().equals("DRAFT"))
                .collect(Collectors.toList());
        return RequestMapper.toDtoList(requests);
    }
}
