package io.hhplus.tdd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum Errors {
    USE("404","Can't Not use Point");

    public String code;
    public String message;

}
