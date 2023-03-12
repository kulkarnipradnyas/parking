package com.parkinglot.parkinglotfees.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ErrorResponseDetailsElement {
    private String title = null;
    private String source = null;
    private String message = null;
    private String messageTemplate = null;
    private Map<String, Object> messagePlaceholders = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorResponseDetailsElement)) return false;

        ErrorResponseDetailsElement that = (ErrorResponseDetailsElement) o;

        if (!title.equals(that.title)) return false;
        if (!source.equals(that.source)) return false;
        if (!message.equals(that.message)) return false;
        if (!messageTemplate.equals(that.messageTemplate)) return false;
        return messagePlaceholders.equals(that.messagePlaceholders);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + messageTemplate.hashCode();
        result = 31 * result + messagePlaceholders.hashCode();
        return result;
    }
}
