package controller;

import model.EligibilityRule;
import model.Veteran;

public class EligibilityController {
    private final EligibilityRule rule;

    public EligibilityController(EligibilityRule rule) {
        this.rule = rule;
    }

    public String evaluateSettlement(Veteran v) {
        boolean postCutoff = v.getRetirementYear() >= rule.getCutoffDate().getYear();
        int years = v.getRetirementYear() - v.getEnlistYear();
        boolean meetsYears = years >= rule.getMinYearsService();

        if (postCutoff && meetsYears) {
            return "Eligible: Post-1997 and meets minimum service years.";
        } else if (postCutoff) {
            return "Review: Post-1997 but service years below threshold.";
        } else {
            return "Not eligible under post-1997 criteria.";
        }
    }
}
