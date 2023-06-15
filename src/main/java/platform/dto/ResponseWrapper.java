package platform.dto;

import lombok.*;

import java.util.Collection;

/**
 * Решает проблему вывода коллекций через ResponseEntity
 * @param <A>
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ResponseWrapper<A> {
    private int count;
    private Collection<A> result;

    public static <A> ResponseWrapper<A> of(Collection<A> results) {
        ResponseWrapper<A> responseWrapper = new ResponseWrapper<>();
        if (results == null) {
            return responseWrapper;
        }
        responseWrapper.result = results;
        responseWrapper.count = results.size();
        return responseWrapper;
    }
}
