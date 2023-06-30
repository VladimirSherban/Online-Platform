package platform.dto;

import lombok.*;

import java.util.Collection;

/**
 * Решает проблему вывода коллекций через ResponseEntity
 *
 * @param <A>
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ResponseWrapper<A> {
    private Integer count;
    private Collection<A> results;

    public static <A> ResponseWrapper<A> of(Collection<A> results) {
        ResponseWrapper<A> responseWrapper = new ResponseWrapper<>();
        if (results == null) {
            return responseWrapper;
        }
        responseWrapper.results = results;
        responseWrapper.count = results.size();
        return responseWrapper;
    }
}
