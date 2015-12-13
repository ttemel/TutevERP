package org.tutev.web.erp.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.tutev.web.erp.entity.genel.KodluListe;
import org.tutev.web.erp.entity.genel.KodluListeTip;
import org.tutev.web.erp.entity.genel.Kullanici;
import org.tutev.web.erp.service.DataService;
import org.tutev.web.erp.service.KodluListeService;
import org.tutev.web.erp.service.KulService;

@Component("dataController")
@Scope("singleton")
public class DataController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2141891297862838000L;

	@Autowired
	private transient DataService dataService;
	@Autowired
	private transient KodluListeService kodluListeService;
	@Autowired
	private transient KulService kulService;
	
	public static Logger logger = Logger.getLogger(DataController.class);

	private List<KodluListe> uyrukListe;
	private List<KodluListe> olcuBirimListe;
	private List<KodluListe> paraBirimListe;
	private List<KodluListe> irsaliyeTurListe;
	private List<KodluListe> yerlesimTipListe;
	private List<KodluListe> uretimTipiListe;
	private Long userCount=Long.MIN_VALUE;
	

	@PostConstruct
	private void init() {
		dataGuncelle();
		
		if (uyrukListe == null || uyrukListe.size() < 1) {
			logger.debug("Referans Data Bulunamadý DB insert Yapýlýyor");
			kodluListeService.save(new KodluListe(null, "TC","Türkiye Cumhuriyeti", KodluListeTip.UYRUK));
			kodluListeService.save(new KodluListe(null, "ABD","Amerika Birleþik Devletleri", KodluListeTip.UYRUK));
			kodluListeService.save(new KodluListe(null, "FR", "Fransa",KodluListeTip.UYRUK));
			logger.debug("Referans Data Bulunamadý DB insert Yapýldý");
			dataGuncelle();
		}
		
		if (irsaliyeTurListe == null || irsaliyeTurListe.size() < 1) {
			logger.debug("İrsaliye Türü : Referans Data Bulunamadý DB insert Yapýlýyor");
			kodluListeService.save(new KodluListe(null, "G","Giriş İrsaliyesi", KodluListeTip.IRSALIYE_TIP));
			kodluListeService.save(new KodluListe(null, "C","Çıkış İrsaliyesi", KodluListeTip.IRSALIYE_TIP));
			logger.debug("İrsaliye Türü : Referans Data Bulunamadý DB insert Yapýldý");
			dataGuncelle();
		}

		if (uretimTipiListe == null || uretimTipiListe.size() < 1) {
			logger.debug("uretimTipiListe Referans verisi Bulunamadý DB insert Yapýlýyor");
			kodluListeService.save(new KodluListe(null, "krtk","Kritik Üretim", KodluListeTip.URETIM_TIP));
			kodluListeService.save(new KodluListe(null, "Zrnl","Zorunlu Üretim", KodluListeTip.URETIM_TIP));
			kodluListeService.save(new KodluListe(null, "icuretim", "Ýç Kaynaklar için Üretim",KodluListeTip.URETIM_TIP));
			kodluListeService.save(new KodluListe(null, "dnmsl", "Dönemsel Üretim",KodluListeTip.URETIM_TIP));
			logger.debug("Referans Data Bulunamadý DB insert Yapýldý");
			dataGuncelle();
		}
		if (paraBirimListe == null || paraBirimListe.size() < 1) {
			logger.debug("paraBirimListe Referans verisi Bulunamadý DB insert Yapýlýyor");
			kodluListeService.save(new KodluListe(null, "TL","Türk Lirası", KodluListeTip.PARA_BIRIM));
			kodluListeService.save(new KodluListe(null, "USD","Amerikan Doları", KodluListeTip.PARA_BIRIM));
			kodluListeService.save(new KodluListe(null, "EURO", "Avrupa Para Birimi",KodluListeTip.PARA_BIRIM));
			logger.debug("Referans Data Bulunamadý DB insert Yapýldý");
			dataGuncelle();
		}	
		if (yerlesimTipListe == null || yerlesimTipListe.size() < 1) {
			logger.debug("paraBirimListe Referans verisi Bulunamadý DB insert Yapýlýyor");
			kodluListeService.save(new KodluListe(null, "IL","IL", KodluListeTip.YERLESIM_TIP));
			kodluListeService.save(new KodluListe(null, "ILCE","ILCE", KodluListeTip.YERLESIM_TIP));
			kodluListeService.save(new KodluListe(null, "ULKE", "ÜLKE",KodluListeTip.YERLESIM_TIP));
			kodluListeService.save(new KodluListe(null, "MAH", "MAHALLE",KodluListeTip.YERLESIM_TIP));
			logger.debug("Referans Data Bulunamadý DB insert Yapýldý");
			dataGuncelle();
		}	
		
		if (userCount == null || userCount < 1) {		
			Kullanici kullanici=new Kullanici();
			kullanici.setDurum(Boolean.TRUE);
			kullanici.setEklemeTarihi(new Date());
			kullanici.setEkleyen("sys");
			kullanici.setPassword("123");
			kullanici.setUsername("admin");
			kulService.save(kullanici);
			dataGuncelle();
		}
		
	}

	public void dataGuncelle() {
		logger.info("Referans Data Yükleniyor");
		uyrukListe = dataService.getByType(KodluListeTip.UYRUK);
		olcuBirimListe = dataService.getByType(KodluListeTip.OLCU_BIRIMI);
		paraBirimListe = dataService.getByType(KodluListeTip.PARA_BIRIM);
		uretimTipiListe = dataService.getByType(KodluListeTip.URETIM_TIP);
		irsaliyeTurListe = dataService.getByType(KodluListeTip.IRSALIYE_TIP);
		yerlesimTipListe=dataService.getByType(KodluListeTip.YERLESIM_TIP);
		userCount=kulService.getUserCount();
		logger.info("Referans Data Yüklendi");
	}

	public void dataUretimGuncelle() {
		logger.info("Üretim Tip Referans Data Yükleniyor");
		uyrukListe = dataService.getByType(KodluListeTip.URETIM_TIP);
	 
		logger.info("Üretim Tip Referans Data Yüklendi");
	}
	
	public List<KodluListe> getUretimTipiListe() {
		return uretimTipiListe;
	}

	public List<KodluListe> getOlcuBirimListe() {
		return olcuBirimListe;
	}

	public List<KodluListe> getParaBirimListe() {
		return paraBirimListe;
	}

	public List<KodluListe> getUyrukListe() {
		return uyrukListe;
	}

	/**
	 * Mehmet Emin IŞIK
	 * @return the irsaliyeTurListe
	 */
	public List<KodluListe> getIrsaliyeTurListe() {
		return irsaliyeTurListe;
	}

	/**
	 * Mehmet Emin IŞIK
	 * @param irsaliyeTurListe the irsaliyeTurListe to set
	 */
	public void setIrsaliyeTurListe(List<KodluListe> irsaliyeTurListe) {
		this.irsaliyeTurListe = irsaliyeTurListe;
	}
	
	public List<KodluListe> getYerlesimTipListe() {
		return yerlesimTipListe;
	}
	
	
}
