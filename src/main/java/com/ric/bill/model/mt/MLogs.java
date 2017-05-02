package com.ric.bill.model.mt;

import java.util.List;

import com.ric.bill.Simple;
import com.ric.bill.Storable;
import com.ric.bill.model.ar.House;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.tr.Serv;

public interface MLogs extends Storable, Simple {

	public abstract String getEntry();

	public abstract void setEntry(String entry);

	public abstract List<Meter> getMeter();

	public abstract void setMeter(List<Meter> meter);

	public abstract Lst getTp();

	public abstract void setTp(Lst tp);

	public abstract List<Vol> getVol();

	public abstract void setVol(List<Vol> vol);

	public abstract Serv getServ();

	public abstract void setServ(Serv serv);

	public void setKlskId(Integer klsk);

	public Integer getKlskObj();

	public abstract Kart getKart();

	public abstract void setKart(Kart kart);

	public abstract House getHouse();

	public abstract void setHouse(House house);

	public abstract List<MeterLogGraph> getInside();

	public abstract List<MeterLogGraph> getOutside();


}