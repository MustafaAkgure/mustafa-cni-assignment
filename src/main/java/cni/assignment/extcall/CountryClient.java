package cni.assignment.extcall;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="rest.config.mledoze-countries.name", url="${rest.config.mledoze-countries.url}")
public interface CountryClient {

	@GetMapping(path = "/master/countries.json")
	List<CountryDto> countries();
}
