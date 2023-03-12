package com.parkinglot.parkinglotfees.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private String title = null;
    private Integer status = null;
    private String code = null;
    private String cause = null;
    private String action = null;
    private  List<ErrorResponseDetailsElement> details = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorResponse)) return false;

        ErrorResponse that = (ErrorResponse) o;

        if (!title.equals(that.title)) return false;
        if (!status.equals(that.status)) return false;
        if (!code.equals(that.code)) return false;
        if (!cause.equals(that.cause)) return false;
        if (!action.equals(that.action)) return false;
        return details.equals(that.details);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + cause.hashCode();
        result = 31 * result + action.hashCode();
        result = 31 * result + details.hashCode();
        return result;
    }
}
