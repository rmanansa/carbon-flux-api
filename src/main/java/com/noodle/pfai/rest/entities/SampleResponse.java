package com.noodle.pfai.rest.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.util.List;

public class SampleResponse {

   @JsonProperty("coils")
   List<OpenCoil> openCoils;

   static class OpenCoil {
      @JsonProperty("piece_id")
      private String pieceID;
      @JsonProperty("product_type")
      private String productType;
      @JsonProperty("customer_order_id")
      private String customerOrderID;
      @JsonProperty("customer_name")
      private String customerName;
      @JsonProperty("release_flag")
      private String releaseFlag;
      @JsonProperty("width_input")
      private double widthInput;
      @JsonProperty("thickness_input")
      private double thicknessInput;
      @JsonProperty("percentage_tk_of_next")
      private String percentageTkOfNext;
      @JsonProperty("weight_input")
      private double weightInput;
      @JsonProperty("width_target")
      private double weightTarget;
      @JsonProperty("thickness_target")
      private double thicknessTarget;
      @JsonProperty("percentage_tkout_of_next")
      private String percentageTkoutOfNext;
      @JsonProperty("no_daughters")
      private int numDaughters;
      @JsonProperty("oil_type")
      private String oilType;
      @JsonProperty("oil_weight")
      private String oilWeight;
      @JsonProperty("date_start_latest")
      private Date dateStartLatest;
      @JsonProperty("due_date")
      private Date dueDate;
      @JsonProperty("date_scheduled")
      private Date dateScheduled;
      @JsonProperty("last_unit_end_date")
      private Date lastUnitEndDate;
      @JsonProperty("shipment_mode")
      private String shipmentMode;
      @JsonProperty("part_number")
      private int partNumber;
      @JsonProperty("rework_flag")
      private String reworkFlag;
      @JsonProperty("trial_flag")
      private String trialFlag;
      @JsonProperty("surface_finish")
      private String surfaceFinish;
      @JsonProperty("roll_change_flag")
      private String rollChangeFlag;
      @JsonProperty("mat_location")
      private String matLocation;
      @JsonProperty("steel_grade")
      private String steelGrade;
      @JsonProperty("side_trimming_flag")
      private String sideTrimmingFlag;
      @JsonProperty("weight_aim")
      private double weightAim;
      @JsonProperty("carbon_equivalent")
      private double carbonEquivalent;
      @JsonProperty("next_production_step")
      private String nextProductionStep;
      @JsonProperty("spm_mode")
      private String spmMode;
      @JsonProperty("remark")
      private String remark;
      @JsonProperty("weight_min")
      private double weightMin;
      @JsonProperty("weight_max")
      private double weightMax;
      @JsonProperty("exposure_class")
      private String exposureClass;
      @JsonProperty("target_wr_roughness")
      private double targetWrRoughness;
      @JsonProperty("material_plan_state")
      private String materialPlanState;
      @JsonProperty("hot_rolling_temp")
      private int hotRollingTemp;
      @JsonProperty("coiling_temperature")
      private int coilingTemperature;
      @JsonProperty("material_block_state")
      private String materialBlockState;
      @JsonProperty("temp_end_diff_aim")
      private int tempEndDiffAim;
      @JsonProperty("temp_end_rft_alm")
      private int tempEndRftAlm;
      @JsonProperty("coating_weight")
      private String coatingWeight;
      @JsonProperty("chemical_treatment")
      private String chemicalTreatment;
      @JsonProperty("spm_elongation")
      private int spmElongation;
      @JsonProperty("production_step_id")
      private int productionStepID;
      @JsonProperty("production_order_id")
      private String productionOrderID;
      @JsonProperty("performance")
      private double performance;
      @JsonProperty("elongation")
      private double elongation;
      @JsonProperty("copper_max")
      private double copperMax;
      @JsonProperty("consuming_industry")
      private String consumingIndustry;
      @JsonProperty("priority")
      private int priority;
      @JsonProperty("scheduling_instructions")
      private String schedulingInstructions;
      @JsonProperty("specificationxx")
      private String specificationXX;
      @JsonProperty("production_step_seq")
      private int productionStepSeq;
      @JsonProperty("steel_grade_id_int")
      private int steelGradeIDInt;
      @JsonProperty("material_id")
      private String materialID;
      @JsonProperty("length_input")
      private double lengthInput;
      @JsonProperty("target_inner_diameter")
      private int targetInnerDiameter;
      @JsonProperty("target_inner_diameter_min")
      private int targetInnerDiameterMin;
      @JsonProperty("target_inner_diameter_max")
      private int targetInnerDiameterMax;
      @JsonProperty("customer_transport_mode")
      private String customerTransportMode;
      @JsonProperty("sequence_phase")
      private int sequencePhase;
   }
}
