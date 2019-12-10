package quiz.apple.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import quiz.apple.model.Retailer;
import quiz.apple.repository.LocRepo;
import quiz.apple.util.EmapHelper;

@Transactional
@Component
@Configuration
public class EMapService {

	@Autowired
	LocRepo locRepo;
	@Autowired
	LocationService locSvc;

	@Value("${map_api_url.baidu}")
	private String baiduMapUrl;

	@Value("${map_api_url.autoNavi}")
	private String autoNaviMapUrl;

	@Value("${app_key.baidu}")
	private String baiduAK;

	@Value("${app_key.autoNavi}")
	private String autoNaviAK;

	public List<Retailer> getRetailerByAddress(String addr, String city) {
		double[] bdCoords = EmapHelper
				.extractBaiduCoords(locSvc.getCoordinates(addr, this.baiduMapUrl, this.baiduAK, city));
		double[] autoCoords = EmapHelper
				.extractAutoNaviCoords(locSvc.getCoordinates(addr, this.autoNaviMapUrl, this.autoNaviAK, city));
		// multiple users may query and insert the retailer
		synchronized (this) {
			List<Retailer> locs = locRepo.findAllRetailersByAddress(addr);
			if (locs != null && locs.size() > 0) {
				locs.get(0).setLongtitude(bdCoords[0]);
				locs.get(0).setLatitude(bdCoords[1]);
				locs.get(1).setLongtitude(autoCoords[0]);
				locs.get(1).setLatitude(autoCoords[1]);
			} else {
				// not existing in db, persist to db
				locs = new ArrayList<Retailer>();
				locs.add(new Retailer(bdCoords[0], bdCoords[1], addr, "baidu"));
				locs.add(new Retailer(autoCoords[0], autoCoords[1], addr, "autoNavi"));
				// System.out.println("locs: " + locs);
			}
			locRepo.save(locs);
			return locs;
		}
	}
}
