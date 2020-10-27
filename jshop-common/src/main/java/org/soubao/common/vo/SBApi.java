package org.soubao.common.vo;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@AllArgsConstructor@NoArgsConstructor
public class SBApi {
    private int status = 1;
    private String msg = "成功";
    private Object result;

    public Object getResult() {
        if (result == null) {
            return new ArrayList<>();
        } else {
            return result;
        }
    }
}
