package com.ric.bill.model.exs;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * Показание прибора учета из ГИС
 * @author lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "METER_VAL", schema="EXS")
@Getter @Setter
public class MeterVal implements java.io.Serializable  {

	public MeterVal() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_METER_VAL")
	@SequenceGenerator(name="SEQ_METER_VAL", sequenceName="EXS.SEQ_METER_VAL", allocationSize=1)
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// счетик в EOLINK к которому прикреплено показание
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private Eolink eolink;

    // дата снятия
    @Column(name = "DT_VAL", updatable = false)
    private Date dtVal;

	// дата-время внесения в ГИС
	@Column(name = "DT_ENTER", updatable = false)
	private Date dtEnter;

	// GUID организации, которая ввела показания, не заполняется, если внесено гражданином
	@Column(name = "ORG_GUID", updatable = false)
	private String orgGuid;

	// коммунальный ресурс, по справочнику НСИ №2
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_LIST", referencedColumnName="ID")
	private Ulist ulist;

    // значение
    @Column(name = "VAL", updatable = false)
    private BigDecimal val;

    // код единицы измерения показаний ПУ (из расширенного классификатора ОКЕИ).
    // Заполняется, если ЕИ показаний ПУ отличается от ЕИ коммунального ресурса по умолчанию
    @Column(name = "UNIT", updatable = false)
    private String unit;

    // кем внесено
    @Column(name = "READING_SOURCE", updatable = false)
    private String readingSource;

    // дата создания
    @Column(name = "DT_CRT", updatable = false)
    private Date dtCrt;

    // дата обновления
    @Column(name = "DT_UPD")
    private Date updDt;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof MeterVal))
	        return false;

	    MeterVal other = (MeterVal)o;

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


    public static final class MeterValBuilder {
        private Integer id;
        // счетик в EOLINK к которому прикреплено показание
        private Eolink eolink;
        // дата-время внесения в ГИС
        private Date dtEnter;
        // GUID организации, которая ввела показания, не заполняется, если внесено гражданином
        private String orgGuid;
        // коммунальный ресурс, по справочнику НСИ №2
        private Ulist ulist;
        // значение
        private BigDecimal val;
        // код единицы измерения показаний ПУ (из расширенного классификатора ОКЕИ).
        // Заполняется, если ЕИ показаний ПУ отличается от ЕИ коммунального ресурса по умолчанию
        private String unit;
        // кем внесено
        private String readingSource;
        // дата создания
        private Date dtCrt;
        // дата обновления
        private Date updDt;

        private MeterValBuilder() {
        }

        public static MeterValBuilder aMeterVal() {
            return new MeterValBuilder();
        }

        public MeterValBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public MeterValBuilder withEolink(Eolink eolink) {
            this.eolink = eolink;
            return this;
        }

        public MeterValBuilder withDtEnter(Date dtEnter) {
            this.dtEnter = dtEnter;
            return this;
        }

        public MeterValBuilder withOrgGuid(String orgGuid) {
            this.orgGuid = orgGuid;
            return this;
        }

        public MeterValBuilder withUlist(Ulist ulist) {
            this.ulist = ulist;
            return this;
        }

        public MeterValBuilder withVal(BigDecimal val) {
            this.val = val;
            return this;
        }

        public MeterValBuilder withUnit(String unit) {
            this.unit = unit;
            return this;
        }

        public MeterValBuilder withReadingSource(String readingSource) {
            this.readingSource = readingSource;
            return this;
        }

        public MeterValBuilder withDtCrt(Date dtCrt) {
            this.dtCrt = dtCrt;
            return this;
        }

        public MeterValBuilder withUpdDt(Date updDt) {
            this.updDt = updDt;
            return this;
        }

        public MeterVal build() {
            MeterVal meterVal = new MeterVal();
            meterVal.setId(id);
            meterVal.setEolink(eolink);
            meterVal.setDtEnter(dtEnter);
            meterVal.setOrgGuid(orgGuid);
            meterVal.setUlist(ulist);
            meterVal.setVal(val);
            meterVal.setUnit(unit);
            meterVal.setReadingSource(readingSource);
            meterVal.setDtCrt(dtCrt);
            meterVal.setUpdDt(updDt);
            return meterVal;
        }
    }
}

