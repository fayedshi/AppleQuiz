package quiz.apple.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quiz.apple.model.Retailer;
import quiz.apple.service.EMapService;
import quiz.apple.util.EmapHelper;

@RestController
@RequestMapping("/emap")
public class LocController {
	private static Map<String, String> retMap = new HashMap<String, String>();
	static {
		retMap.put("Apple授权经销商(苏宁北京⻄⼤望路店)", "北京");
		retMap.put("Apple授权经销商(苏宁沈阳⻓江街店)", "沈阳");
		retMap.put("Apple授权经销商(苏宁成都⾃建店)", "成都");
	}

	@Autowired
	EMapService mapSvc;

	// show retailer
	@GetMapping("/retailer/{address}")
	public List<Retailer> showRetailer(@PathVariable("address") String address) {
		return mapSvc.getRetailerByAddress(address, retMap.get(address));
	}

	// show distance of individual retailer
	@GetMapping("/dist/{address}")
	public Map<String, Double> showDist(@PathVariable("address") String address) {
		Map<String, Double> dists = new HashMap<String, Double>();
		List<Retailer> rtlr = mapSvc.getRetailerByAddress(address, retMap.get(address));
		System.out.println("converting " + address);
		double[] converted = EmapHelper.bdEncrypt(rtlr.get(0).getLongtitude(), rtlr.get(0).getLatitude());
		System.out.println("converted " + (converted[0] + ", " + converted[1]));
		double dist = EmapHelper.getDistance(converted[0], converted[1], rtlr.get(1).getLongtitude(),
				rtlr.get(1).getLatitude());
		dists.put(address, dist);
		return dists;
	}

	// show distance of all retailers
	@GetMapping("/dist")
	public Map<String, Double> showAllDist() {
		Map<String, Double> dists = new HashMap<String, Double>();
		for (String add : retMap.keySet()) {
			List<Retailer> rts = mapSvc.getRetailerByAddress(add, retMap.get(add));
			System.out.println("converting " + add);
			double[] converted = EmapHelper.bdEncrypt(rts.get(0).getLongtitude(), rts.get(0).getLatitude());
			System.out.println("converted " + (converted[0] + ", " + converted[1]));
			double dist = EmapHelper.getDistance(converted[0], converted[1], rts.get(1).getLongtitude(),
					rts.get(1).getLatitude());
			dists.put(add, dist);
		}
		return dists;
	}
}
