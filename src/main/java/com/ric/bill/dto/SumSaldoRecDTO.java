package com.ric.bill.dto;

import java.math.BigDecimal;

import javax.annotation.Generated;

import lombok.Getter;
import lombok.Setter;

/*
 * DTO для хранения записи сальдо, дебет, кредит
 * @author - Lev
 * @ver 1.00
 */
@Getter @Setter
public class SumSaldoRecDTO {

	// вх.дебет
	BigDecimal indebet;
	// вх.кредит
	BigDecimal inkredit;
	// исх.дебет
	BigDecimal outdebet;
	// исх.кредит
	BigDecimal outkredit;
	// оплата
	BigDecimal payment;
	// вх.суммарное сальдо
	BigDecimal inSal;
	// исх.суммарное сальдо
	BigDecimal outSal;

	@Generated("SparkTools")
	private SumSaldoRecDTO(Builder builder) {
		this.indebet = builder.indebet;
		this.inkredit = builder.inkredit;
		this.outdebet = builder.outdebet;
		this.outkredit = builder.outkredit;
		this.payment = builder.payment;
		this.inSal = builder.inSal;
		this.outSal = builder.outSal;
	}

	/**
	 * Creates builder to build {@link SumSaldoRecDTO}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link SumSaldoRecDTO}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private BigDecimal indebet;
		private BigDecimal inkredit;
		private BigDecimal outdebet;
		private BigDecimal outkredit;
		private BigDecimal payment;
		private BigDecimal inSal;
		private BigDecimal outSal;

		private Builder() {
		}

		public Builder withIndebet(BigDecimal indebet) {
			this.indebet = indebet;
			return this;
		}

		public Builder withInkredit(BigDecimal inkredit) {
			this.inkredit = inkredit;
			return this;
		}

		public Builder withOutdebet(BigDecimal outdebet) {
			this.outdebet = outdebet;
			return this;
		}

		public Builder withOutkredit(BigDecimal outkredit) {
			this.outkredit = outkredit;
			return this;
		}

		public Builder withPayment(BigDecimal payment) {
			this.payment = payment;
			return this;
		}

		public Builder withInSal(BigDecimal inSal) {
			this.inSal = inSal;
			return this;
		}

		public Builder withOutSal(BigDecimal outSal) {
			this.outSal = outSal;
			return this;
		}

		public SumSaldoRecDTO build() {
			return new SumSaldoRecDTO(this);
		}
	}
}
