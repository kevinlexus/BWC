package com.ric.bill.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO - Организация
 * @author lev
 *
 */
@Getter @Setter
public class OrgDTO extends BaseDTO {

	public OrgDTO(Integer id, String cd, String name) {
		super(id, cd, name);
	}

	// ИНН
	String inn;
	// БИК банка
	String bik;
	// расчетный счет в банке
	String operAcc;
	// расчетный счет для ГИС ЖКХ
	String operAccGis;

}
