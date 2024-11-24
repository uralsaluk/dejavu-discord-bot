package com.ural.dejavutools.discordbot.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Generated;


public class BaseResponse implements Serializable {


  @JsonProperty("result")
  private @Valid @NotNull ResultResponse result;


  public BaseResponse() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseResponse that = (BaseResponse) o;
    return result.equals(that.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(new Object[]{this.result});
  }

  @Override
  public String toString() {
    return "BaseResponse{" +
        "result=" + result +
        '}';
  }

  @Generated
  public ResultResponse getResult() {
    return this.result;
  }

  @JsonProperty("result")
  @Generated
  public void setResult(final ResultResponse result) {
    this.result = result;

  }
}
