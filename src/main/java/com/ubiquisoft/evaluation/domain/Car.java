package com.ubiquisoft.evaluation.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {
	private String year;
	private String make;
	private String model;

	private List<Part> parts;

	public Map<PartType, Integer> getMissingPartsMap() {
		/*
		 * Return map of the part types missing.
		 *
		 * Each car requires one of each of the following types:
		 *      ENGINE, ELECTRICAL, FUEL_FILTER, OIL_FILTER
		 * and four of the type: TIRE
		 *
		 * Example: a car only missing three of the four tires should return a map like this:
		 *
		 *      {
		 *          "TIRE": 3
		 *      }
		 */

		Map<PartType, Integer> missingPartsMap=new HashMap<>();
		long count=0;
		/**
		 * Looping thru available part types and checking their prescence in the car, by checking against each part in the car
		 */
		for(PartType p: PartType.values()) {
			count=parts.stream().filter(part -> p.equals(part.getType())).count();
			
			if(count<=0) {
				missingPartsMap.put(p, 1);
			}
			if(PartType.TIRE.equals(p) && count<4) {
				missingPartsMap.put(p, (int)(4-count));
			}
		}
		
		return missingPartsMap;	
	}
	
	/**
	 * Gets the damaged parts information of the car.
	 * @return List of damaged parts. Empty list if no damaged parts.
	 */
	public List<Part> getDamagedParts(){
		return this.getParts().stream().filter(part -> {
			ConditionType condition = part.getCondition();
			return (!ConditionType.NEW.equals(condition) && !ConditionType.GOOD.equals(condition) && !ConditionType.WORN.equals(condition));
		}).collect(Collectors.toList());
	}
	
	/**
	 * Gets the missing data of the car. Data checked: Make, Model, Year.
	 * @return List of missing data of the car. Empty list if all data are present.
	 */
	public List<String> getMissingData(){
		List<String> missingData=new ArrayList<>();
		if(null == this.getMake() || "".equals(this.getMake())){
			missingData.add("Make");
		}
		if(null == this.getModel() || "".equals(this.getModel())){
			missingData.add("Model");
		}
		if(null == this.getYear() || "".equals(this.getYear())){
			missingData.add("Year");
		}
		return missingData;
	}

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
