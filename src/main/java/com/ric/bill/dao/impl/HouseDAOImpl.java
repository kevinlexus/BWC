package com.ric.bill.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.TemporalType;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;

import com.ric.bill.ResultSet;
import com.ric.bill.dao.HouseDAO;
import com.ric.bill.model.ar.House;
import com.ric.bill.model.bs.Org;



/**
 * DAO House
 * @author Lev
 * @version 1.00
 *
 */
@Repository
@Slf4j
public class HouseDAOImpl implements HouseDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;
    //@Autowired
    //private Config config;
    
    static final String STATEMENT_SQLMAP = "Statement-SQL-Mapping";
    
    //конструктор
    public HouseDAOImpl() {
    	
    }
    
	/**
	 * Поиск домов по условию
	 * @param houseId - наличию ID дома
	 * @param areaId - наличию ID области
	 * @param tempLskId - наличию ID списка лиц.счетов
	 * @param dt1 - начальная дата расчета 
	 * @param dt2 - конечная дата расчета 
	 * @return
	 */
	public List<House> findAll2(Integer houseId, Integer areaId, Integer tempLskId, Date dt1, Date dt2) {
		@SqlResultSetMapping(name= STATEMENT_SQLMAP, classes = { //эту часть кода можно закинуть в любое место
		        @ConstructorResult(targetClass = ResultSet.class,
		            columns = {
		                @ColumnResult(name="id",type = Integer.class)
		            }
		        )
		    }) @Entity class SQLMappingCfgEntity{@Id int id;} // <- walkaround

		Query q;
		List<House> lstHouse = null;
		try {
			if (areaId != null) {
				// по городу
				q = em.createNativeQuery("select distinct h.id "+
						   "from ar.house h, ar.kart k, ar.kw kw, bs.org o, bs.org u, ar.street s  "+
						   "where k.fk_kw = kw.id "+
						   "and h.id = kw.fk_house "+//and h.id in (187, 168, 2009) "+  //TODO УБРАТЬ!УБРАТЬ!УБРАТЬ!УБРАТЬ!УБРАТЬ!УБРАТЬ!
						   //"and ? between k.dt1 and k.dt2 "+
						   "and (:dt1 between k.dt1 and k.dt2 or :dt2 between k.dt1 and k.dt2) "+// ред.31.10.2017 здесь использовать даты таким образом. (участв.лиц.тек периода)
						   "and o.parent_id=u.id "+
						   "and h.fk_street = s.id and s.fk_area = :areaId "+
						   "and k.fk_uk = u.id "+
						   "order by h.id ",  STATEMENT_SQLMAP);
				q.setParameter("dt1", dt1, TemporalType.DATE);
				q.setParameter("dt2", dt2, TemporalType.DATE);
				q.setParameter("areaId", areaId);
			} else if (houseId != null) {
				// по дому
				q = em.createNativeQuery("select distinct h.id "+
						   "from ar.house h, ar.kart k, ar.kw kw, bs.org o, bs.org u  "+
						   "where k.fk_kw = kw.id "+
						   "and h.id = kw.fk_house "+
						   //"and ? between k.dt1 and k.dt2 "+
						   "and (:dt1 between k.dt1 and k.dt2 or :dt2 between k.dt1 and k.dt2) "+// ред.31.10.2017 здесь использовать даты таким образом. (участв.лиц.тек периода)
						   "and o.parent_id=u.id and h.id= :houseId "+
						   "and k.fk_uk = u.id "+
						   "order by h.id ",  STATEMENT_SQLMAP);
				q.setParameter("dt1", dt1, TemporalType.DATE);
				q.setParameter("dt2", dt2, TemporalType.DATE);
				q.setParameter("houseId", houseId);
			} else if (tempLskId != null) {
				// по списку лиц.счетов, без контроля даты!!!
				q = em.createNativeQuery("select distinct h.id "+
						   "from ar.house h, ar.kart k, ar.kw kw, bs.org o, bs.org u, fn.temp_lsk tm  "+
						   "where k.fk_kw = kw.id and k.lsk=tm.lsk and tm.fk_id=? "+
						   "and h.id = kw.fk_house "+
						   "and o.parent_id=u.id "+
						   "and k.fk_uk = u.id "+
						   "order by h.id ",  STATEMENT_SQLMAP);
				q.setParameter(1, tempLskId);
			} else {
				// весь фонд
				q = em.createNativeQuery("select distinct h.id "+
						   "from ar.house h, ar.kart k, ar.kw kw, bs.org o, bs.org u  "+
						   "where k.fk_kw = kw.id "+
						   "and h.id = kw.fk_house "+
						   "and o.parent_id=u.id "+
						   "and k.fk_uk = u.id "+
						   "and (:dt1 between k.dt1 and k.dt2 or :dt2 between k.dt1 and k.dt2) "+// ред.31.10.2017 здесь использовать даты таким образом. (участв.лиц.тек периода)
						   "order by h.id ",  STATEMENT_SQLMAP);
				q.setParameter("dt1", dt1, TemporalType.DATE);
				q.setParameter("dt2", dt2, TemporalType.DATE);
			}
			
			List<ResultSet> lst = q.getResultList();
			lstHouse = new ArrayList<House>();
			for (ResultSet rs: lst) {
				log.info("Найден дом: houseid="+rs.getId(), 2);
				lstHouse.add(em.find(House.class, rs.getId()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return lstHouse;
		
	}
    
}
