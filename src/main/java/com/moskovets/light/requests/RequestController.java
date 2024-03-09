package com.moskovets.light.requests;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
@Tag(name = "Заявки")
public class RequestController {

    private final RequestService requestService;

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать черновик заявки")
    @PreAuthorize("hasRole('USER')")
    public RequestDto createRequestDraft(@PathVariable Long userId,
                                         @RequestBody String text) {
        return requestService.createRequestDraft(userId, text);
    }

    @PostMapping("/user/{userId}/user-requests/draft/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Отправить заявку")
    @PreAuthorize("hasRole('USER')")
    public RequestDto sendRequest(@PathVariable Long userId,
                                  @PathVariable Long requestId) {
        return requestService.sendRequest(userId, requestId);
    }

    @PatchMapping("/user/{userId}/user-requests/draft/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить черновик заявки")
    @PreAuthorize("hasRole('USER')")
    public RequestDto updateRequestDraft(@PathVariable Long userId,
                                         @PathVariable Long requestId,
                                         @RequestBody String text) {
        return requestService.updateRequestDraft(userId, requestId, text);
    }

    @GetMapping("/operator/sent")
    @Operation(summary = "Получить отправленные заявки всех пользователей")
    @PreAuthorize("hasRole('OPERATOR')")
    public List<RequestDto> getSentRequests(@RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                  @RequestParam(name = "size", defaultValue = "5") @Positive Integer size,
                                                  @RequestParam(name = "sort", defaultValue = "desc") String sort) {
        return requestService.getSentRequests(from, size, sort);
    }

    @GetMapping("/operator/{userId}/sent")
    @Operation(summary = "Получить отправленные заявки пользователя")
    @PreAuthorize("hasRole('OPERATOR')")
    public List<RequestDto> getSentRequestsByUser(@PathVariable(name = "userId") Long userId,
                                                  @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                  @RequestParam(name = "size", defaultValue = "5") @Positive Integer size,
                                                  @RequestParam(name = "sort", defaultValue = "desc") String sort) {
        return requestService.getSentRequestsByUserId(userId, from, size, sort);
    }

    @PatchMapping("/operator/user-requests/{requestId}/confirm")
    @Operation(summary = "Подтвердить заявку")
    @PreAuthorize("hasRole('OPERATOR')")
    public RequestDto confirmRequest(@PathVariable(name = "requestId") Long requestId) {
        return requestService.confirmRequest(requestId);
    }

    @PatchMapping("/operator/user-requests/{requestId}/reject")
    @Operation(summary = "Отклонить заявку")
    @PreAuthorize("hasRole('OPERATOR')")
    public RequestDto rejectRequest(@PathVariable(name = "requestId") Long requestId) {
        return requestService.rejectRequest(requestId);
    }

    @GetMapping("/operator/user-requests/{requestId}")
    @Operation(summary = "Получить заявку по ID")
    @PreAuthorize("hasRole('OPERATOR')")
    public RequestDto getRequestsById(@PathVariable(name = "requestId") Long requestId) {
        return requestService.getRequestById(requestId);
    }

    @GetMapping("/operator/search/{username}")
    @Operation(summary = "Найти заявки пользователя по логину/части логина")
    @PreAuthorize("hasRole('OPERATOR')")
    public List<RequestDto> searchSentRequestsByUsername(@PathVariable(name = "username") String username,
                                           @RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "5") int size) {
        return requestService.searchSentRequestsByUsername(username, from, size);
    }

    @GetMapping("/admin/user-requests")
    @Operation(summary = "Получить все заявки")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RequestDto> getAllRequests(@RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(name = "size", defaultValue = "5") @Positive Integer size,
                                           @RequestParam(name = "sort", defaultValue = "desc") String sort) {
        return requestService.getAllRequests(from, size, sort);
    }
}
