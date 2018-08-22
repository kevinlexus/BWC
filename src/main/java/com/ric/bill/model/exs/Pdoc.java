package com.ric.bill.model.exs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;


/**
 * Платежный документ в ГИС
 * @author lev
 * @version 1.01
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PDOC", schema="EXS")
@Getter @Setter
public class Pdoc implements java.io.Serializable  {

	public Pdoc() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PDOC")
	@SequenceGenerator(name="SEQ_PDOC", sequenceName="EXS.SEQ_PDOC", allocationSize=1)
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// GUID во внешней системе
	@Column(name = "GUID", updatable = true, nullable = true)
	private String guid;

	// номер документа в биллинге
	@Column(name = "CD", updatable = true, nullable = true)
	private String cd;

	// уникальный номер во внешней системе
	@Column(name = "UNIQNUM")
	private String un;

	// лицевой счет в EOLINK к которому прикреплен ПД
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private Eolink eolink;

	// транспортный GUID объекта
	@Column(name = "TGUID", updatable = true, nullable = true)
	private String tguid;

	// статус загрузки в ГИС (0-добавлен на загрузку, 1-загружен, 2-отменён)
	@Column(name = "STATUS", updatable = true, nullable = false)
	private Integer status;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_PDOC", referencedColumnName="ID")
	private List<Notif> notif = new ArrayList<Notif>(0);

	// дата ПД
	@Column(name = "DT")
	private Date dt;

	// статус в ГИС (1-действующий, 0-отменён)
	@Column(name = "V", updatable = true, nullable = false)
	private Integer v;

	// код ошибки, при загрузке ПД в ГИС (0-нет ошибки, 1-есть)
	@Column(name = "ERR", updatable = true, nullable = false)
	private Integer err;

	// результат последней отправки ПД
	@Column(name = "RESULT", updatable = true, nullable = true)
	private String result;

	// комментарий импорта-экспорта
	@Column(name = "COMM", updatable = true, nullable = true)
	private String comm;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Pdoc))
	        return false;

	    Pdoc other = (Pdoc)o;

	    if (id == other.getId()) return true;
	    if (id == null) return false;

	    // equivalence by id
	    return id.equals(other.getId());
	}

	@Override
	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}


	public static final class PdocBuilder {
		private Integer id;
		// GUID во внешней системе
        private String guid;
		// номер документа в биллинге
        private String cd;
		// уникальный номер во внешней системе
        private String un;
		// лицевой счет в EOLINK к которому прикреплен ПД
        private Eolink eolink;
		// транспортный GUID объекта
        private String tguid;
		// статус загрузки в ГИС (0-добавлен на загрузку, 1-загружен, 2-отменён)
        private Integer status;
		private List<Notif> notif = new ArrayList<Notif>(0);
		// дата ПД
        private Date dt;
		// статус в ГИС (1-действующий, 0-отменён)
        private Integer v;
		// код ошибки, при загрузке ПД в ГИС (0-нет ошибки, 1-есть)
        private Integer err;
		// результат последней отправки ПД
        private String result;
		// комментарий импорта-экспорта
        private String comm;

		private PdocBuilder() {
		}

		public static PdocBuilder aPdoc() {
			return new PdocBuilder();
		}

		public PdocBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public PdocBuilder withGuid(String guid) {
			this.guid = guid;
			return this;
		}

		public PdocBuilder withCd(String cd) {
			this.cd = cd;
			return this;
		}

		public PdocBuilder withUn(String un) {
			this.un = un;
			return this;
		}

		public PdocBuilder withEolink(Eolink eolink) {
			this.eolink = eolink;
			return this;
		}

		public PdocBuilder withTguid(String tguid) {
			this.tguid = tguid;
			return this;
		}

		public PdocBuilder withStatus(Integer status) {
			this.status = status;
			return this;
		}

		public PdocBuilder withNotif(List<Notif> notif) {
			this.notif = notif;
			return this;
		}

		public PdocBuilder withDt(Date dt) {
			this.dt = dt;
			return this;
		}

		public PdocBuilder withV(Integer v) {
			this.v = v;
			return this;
		}

		public PdocBuilder withErr(Integer err) {
			this.err = err;
			return this;
		}

		public PdocBuilder withResult(String result) {
			this.result = result;
			return this;
		}

		public PdocBuilder withComm(String comm) {
			this.comm = comm;
			return this;
		}

		public Pdoc build() {
			Pdoc pdoc = new Pdoc();
			pdoc.setId(id);
			pdoc.setGuid(guid);
			pdoc.setCd(cd);
			pdoc.setUn(un);
			pdoc.setEolink(eolink);
			pdoc.setTguid(tguid);
			pdoc.setStatus(status);
			pdoc.setNotif(notif);
			pdoc.setDt(dt);
			pdoc.setV(v);
			pdoc.setErr(err);
			pdoc.setResult(result);
			pdoc.setComm(comm);
			return pdoc;
		}
	}
}

