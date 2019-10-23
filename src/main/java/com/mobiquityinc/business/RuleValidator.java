package com.mobiquityinc.business;

import com.mobiquityinc.DTO.Packet;
import com.mobiquityinc.exception.BusinessException;

public interface RuleValidator {

	public void validateConstraints(Packet packet) throws BusinessException;
	public String validatePacketGoal(Packet packet);
}
