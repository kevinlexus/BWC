package com.ric.bill.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import com.ric.bill.ResultSetKlsk;
import com.ric.bill.dao.KartDAO;
import com.ric.bill.model.ar.Kart;


@Repository
public class KartDAOImpl implements KartDAO {

    //@Autowired
    //private Config config;

    @PersistenceContext
    private EntityManager em;
    
    static final String STATEMENT_SQLMAP = "Statement-SQL-Mapping2";

    //конструктор
    public KartDAOImpl() {
    	
    }
    
    /**
     * Получить список лиц.счетов
     * @param houseId
     * @param areaId
     * @param tempLskId
     * @param dt1
     * @return
     */
	public List<Kart> findAll(Integer houseId, Integer areaId, Integer tempLskId, Date dt1, Date dt2) {
		@SqlResultSetMapping(name= STATEMENT_SQLMAP, classes = { //эту часть кода можно закинуть в любое место
		        @ConstructorResult(targetClass = ResultSetKlsk.class,
		            columns = {
		                @ColumnResult(name="lsk",type = Integer.class)
		            }
		        )
		    }) @Entity class SQLMappingCfgEntity{@Id String lsk;} // <- walkaround


		Query q;
		List<Kart> lstKart = null;
		try {

			if (areaId != null) {
				// по городу
				q = em.createNativeQuery("select distinct k.lsk "+
						   "from ar.house h, ar.kart k, ar.kw kw, bs.org o, bs.org u, ar.street s  "+
						   "where k.fk_kw = kw.id "+
						   "and h.id = kw.fk_house "+//and h.id in (187, 168, 2009) and 1=2 "+  //TODO УБРАТЬ!УБРАТЬ!УБРАТЬ!УБРАТЬ!УБРАТЬ!УБРАТЬ!
						   "and o.parent_id=u.id "+
						   "and k.fk_uk = u.id "+
						   //"and (:dt1 between k.dt1 and k.dt2 or :dt2 between k.dt1 and k.dt2) "+ ред.31.10.2017 НЕЛЬЗЯ использовать так даты, так как не будут найдены и пересчитаны лс
						   "and h.fk_street = s.id and s.fk_area = :areaId "+ // с датой позже текущего периода (ошибочно начисленные, и т.п.) TODO подумать!
						   "order by k.lsk ",  STATEMENT_SQLMAP);
				/*q.setParameter("dt1", dt1,
						TemporalType.DATE);
				q.setParameter("dt2", dt2,
						TemporalType.DATE);*/
				q.setParameter("areaId", areaId);
				
			} else if (houseId != null) {
				// по дому
				q = em.createNativeQuery("select distinct k.lsk "+
						   "from ar.house h, ar.kart k, ar.kw kw, bs.org o, bs.org u  "+
						   "where k.fk_kw = kw.id "+
						   "and h.id = kw.fk_house "+
						   "and o.parent_id=u.id "+
						   "and k.fk_uk = u.id "+
						   //"and (:dt1 between k.dt1 and k.dt2 or :dt2 between k.dt1 and k.dt2) "+ ред.31.10.2017 НЕЛЬЗЯ использовать так даты, так как не будут найдены и пересчитаны лс
						   "and h.id = :houseId "+
						   "order by k.lsk ",  STATEMENT_SQLMAP);
				/*q.setParameter("dt1", dt1,
						TemporalType.DATE);
				q.setParameter("dt2", dt2,
						TemporalType.DATE);*/
				q.setParameter("houseId", houseId);
			} else if (tempLskId != null) {
				// по списку лиц.счетов
				q = em.createNativeQuery("select t.lsk "+
						   "from fn.temp_lsk t where t.fk_id=?"+
						   "order by t.lsk ",  STATEMENT_SQLMAP);
				q.setParameter(1, tempLskId);
			} else {
				// весь фонд
				q = em.createNativeQuery("select distinct k.lsk "+
						   "from ar.house h, ar.kart k, ar.kw kw, bs.org o, bs.org u  "+
						   "where k.fk_kw = kw.id "+
						   "and h.id = kw.fk_house "+
//						   "and o.reu in ('Z4', 'F4', 'J4', 'G4') /*'D8'*/ "+
						   "and o.parent_id=u.id /*and h.id=7468*/ "+ // and (k.lsk between 1 and 300 or k.lsk between 1500 and 1520) "+
						   "and k.fk_uk = u.id "+
						   //"and (:dt1 between k.dt1 and k.dt2 or :dt2 between k.dt1 and k.dt2) "+ ред.31.10.2017 НЕЛЬЗЯ использовать так даты, так как не будут найдены и пересчитаны лс
						   "order by k.lsk ",  STATEMENT_SQLMAP);
				/*q.setParameter("dt1", dt1,
						TemporalType.DATE);
				q.setParameter("dt2", dt2,
						TemporalType.DATE);*/
			}
			
			
			List<ResultSetKlsk> lst = q.getResultList();
			lstKart = new ArrayList<Kart>();
			for (ResultSetKlsk rs: lst) {
				lstKart.add(em.find(Kart.class, rs.getLsk()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return lstKart;
		
	}
    
    
}
