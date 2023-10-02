package com.example.postme.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ErrorHandlerTest {
    @InjectMocks
    private ErrorHandler errorHandler;

    @Test
    void handleNullExceptionTest() {
        ApiError apiError = errorHandler.handleNullException(new NullPointerException("NullPointerException"));

        assertEquals(apiError.getReason(), "NullPointerException");
        assertEquals(apiError.getMessage(), "The required object was not found.");
        assertEquals(apiError.getStatus(), "NOT_FOUND");
    }

    @Test
    void handleValidationExceptionTest() {
        ApiError apiError = errorHandler.handleValidationException(new MissingServletRequestParameterException("parameterName", "parameterType"));

        assertEquals(apiError.getReason(), "Required request parameter 'parameterName' for method parameter type parameterType is not present");
        assertEquals(apiError.getStatus(), "BAD_REQUEST");
        assertEquals(apiError.getMessage(), "Incorrectly made request");
    }

    @Test
    void handleDuplicateExceptionTest() {
        ApiError apiError = errorHandler.handleDuplicateException(new DuplicateException("DuplicateException"));

        assertEquals(apiError.getReason(), "DuplicateException");
        assertEquals(apiError.getMessage(), "Integrity constraint has been violated.");
        assertEquals(apiError.getStatus(), "CONFLICT");
    }

    @Test
    void handleWrongConditionExceptionTest() {
        ApiError apiError = errorHandler.handleWrongConditionException(new WrongConditionException("Exception"));

        assertEquals(apiError.getReason(), "Exception");
        assertEquals(apiError.getMessage(), "For the requested operation the conditions are not met.");
        assertEquals(apiError.getStatus(), "FORBIDDEN");
    }
}