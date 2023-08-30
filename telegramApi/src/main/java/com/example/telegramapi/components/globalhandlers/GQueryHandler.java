package com.example.telegramapi.components.globalhandlers;

import com.example.telegramapi.components.QueryHandler;
import com.example.telegramapi.components.RequestHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.services.SessionService;
import feign.FeignException;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GQueryHandler extends RequestHandler {
    private final List<QueryHandler> queryHandlers;

    private final SessionService sessionService;

    public GQueryHandler(List<QueryHandler> queryHandlers, SessionService sessionService) {
        this.queryHandlers = queryHandlers
                .stream()
                .sorted(Comparator
                        .comparing(QueryHandler::isInteger))
                .collect(Collectors.toList());
        this.sessionService = sessionService;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isBackQuery(request.getUpdate());
    }

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session = sessionService.checkUseData(session, request);
        try {
            for (QueryHandler queryHandler : queryHandlers) {
                if ((queryHandler.getCallbackQuery(session.getUserData().getUserSettings().getInterfaceLang()).equals(request.getUpdate().getCallbackQuery().getData()) || queryHandler.isInteger())) {
                    queryHandler.handle(request);
                    return;
                }
            }
        } catch (FeignException ex) {
            if (ex.status() == 400) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
