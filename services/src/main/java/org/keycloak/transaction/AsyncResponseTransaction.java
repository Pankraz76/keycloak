/*
 * Copyright 2025 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keycloak.transaction;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakTransaction;
import org.keycloak.models.KeycloakTransactionManager;
import org.keycloak.services.ErrorPage;
import org.keycloak.services.messages.Messages;

import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.core.Response;

/**
 * When using {@link AsyncResponse#resume(Object)} directly in the code, the response is returned before all changes 
 * done withing this execution are committed. Therefore we need some mechanism that resumes the AsyncResponse after all
 * changes are successfully committed. This can be achieved by enlisting an instance of AsyncResponseTransaction into
 * the main transaction using {@link org.keycloak.models.KeycloakTransactionManager#enlistAfterCompletion(KeycloakTransaction)}.
 */
public class AsyncResponseTransaction implements KeycloakTransaction {

    private final KeycloakSession session;
    private final AsyncResponse responseToFinishInTransaction;
    private final Response responseToSend;

    /**
     * This method creates a new AsyncResponseTransaction instance that resumes provided AsyncResponse
     * {@code responseToFinishInTransaction} with given Response {@code responseToSend}. The transaction is enlisted 
     * to {@link KeycloakTransactionManager}.
     *
     * @param session Current KeycloakSession
     * @param responseToFinishInTransaction AsyncResponse to be resumed on {@link KeycloakTransactionManager} commit/rollback.
     * @param responseToSend Response to be sent
     */
    public static void finishAsyncResponseInTransaction(KeycloakSession session, AsyncResponse responseToFinishInTransaction, Response responseToSend) {
        session.getTransactionManager().enlistAfterCompletion(new AsyncResponseTransaction(session, responseToFinishInTransaction, responseToSend));
    }
    
    private AsyncResponseTransaction(KeycloakSession session, AsyncResponse responseToFinishInTransaction, Response responseToSend) {
        this.session = session;
        this.responseToFinishInTransaction = responseToFinishInTransaction;
        this.responseToSend = responseToSend;
    }

    @Override
    public void begin() {

    }

    @Override
    public void commit() {
        responseToFinishInTransaction.resume(responseToSend);
    }

    @Override
    public void rollback() {
        responseToFinishInTransaction.resume(ErrorPage.error(session, null, Response.Status.INTERNAL_SERVER_ERROR, Messages.INTERNAL_SERVER_ERROR));
    }

    @Override
    public void setRollbackOnly() {

    }

    @Override
    public boolean getRollbackOnly() {
        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
