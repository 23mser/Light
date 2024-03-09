package com.moskovets.light.requests;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RequestMapper {
    public RequestDto toDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .text(request.getText())
                .created(request.getCreated())
                .requesterId(request.getId())
                .requesterName(request.getRequesterName())
                .requesterPhone(request.getRequesterPhone())
                .status(request.getStatus())
                .build();
    }

    public  List<RequestDto> toDtoList(List<Request> requests) {
        return requests.stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }
}
