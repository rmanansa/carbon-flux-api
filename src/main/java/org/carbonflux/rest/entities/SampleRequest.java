package org.carbonflux.rest.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class SampleRequest {

   @JsonProperty("program_id")
   @NotNull
   @Size(min = 2, max = 40)
   private String programID;
   @JsonProperty("production_line_name")
   private String productionLineName;
   @JsonProperty("program_state")
   private int programState;
   @JsonProperty("program_style")
   private int programStyle;
   @JsonProperty("start_planned")
   private String startPlanned;
   @JsonProperty("remark_pg")
   private String remarkPG;
   @JsonProperty("duration")
   private int duration;
   @JsonProperty("program_type")
   private int programType;
   @JsonProperty("pgl")
   private List<PGL> pglList;

   static class PGL {
      @JsonProperty("program_id")
      private String programID;
      @JsonProperty("pgl_level1_pgl")
      private int pglLevel1PGL;
      @JsonProperty("pgl_level2_pgl")
      private int pglLevel2PGL;
      @JsonProperty("production_line_name")
      private String productionLineName;
      @JsonProperty("production_step_id")
      private int productionStepID;
      @JsonProperty("production_order_id")
      private String productionOrderID;
      @JsonProperty("material_id_pgl")
      private String materialIdPGL;
      @JsonProperty("piece_id")
      private String pieceID;
      @JsonProperty("duration_pgl")
      private int durationPGL;
      @JsonProperty("weight")
      private double weight;
      @JsonProperty("program_item_no")
      private String programItemNum;
      @JsonProperty("treatment_no")
      private int treatmentNum;
      @JsonProperty("steel_grade_id_int")
      private int steelGradeIdInt;
      @JsonProperty("no_daughters")
      private int numDaughters;
      @JsonProperty("remark_pgl")
      private String remarkPGL;
      @JsonProperty("inner_diameter")
      private int innerDiameter;
      @JsonProperty("roll_change")
      private int rollChange;
      @JsonProperty("width_target")
      private double widthTarget;
      @JsonProperty("thickness_target")
      private double thicknessTarget;
      @JsonProperty("oil_type")
      private String oilType;
      @JsonProperty("oil_weight")
      private String oilWeight;
   }
}
