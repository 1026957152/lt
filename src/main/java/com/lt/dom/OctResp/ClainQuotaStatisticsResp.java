package com.lt.dom.OctResp;


import com.lt.dom.otcenum.EnumQuotaType;

import java.util.List;


public class ClainQuotaStatisticsResp {




    private Long totalCount;
    private Long totalAmount;
    private int allocatedQuotaCount;
    private long allocatedVoucherCount;
    private long unAllocatedVoucherCount;
    private List<ClainQuotaPojo> clainQuotas;
    public static class ClainQuotaPojo {


        private long compaign;

        private EnumQuotaType type;

        private long quota;
        private boolean clain;  // 是否需要 clain
        private long supplier; // 公司配额
        private String scenario;
        private String scenarioCode;

        public EnumQuotaType getType() {
            return type;
        }

        public void setType(EnumQuotaType type) {
            this.type = type;
        }

        public long getQuota() {
            return quota;
        }

        public void setQuota(long quota) {
            this.quota = quota;
        }

        public String getScenario() {
            return scenario;
        }

        public void setScenario(String scenario) {
            this.scenario = scenario;
        }

        public String getScenarioCode() {
            return scenarioCode;
        }

        public void setScenarioCode(String scenarioCode) {
            this.scenarioCode = scenarioCode;
        }
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setAllocatedQuotaCount(int allocatedQuotaCount) {
        this.allocatedQuotaCount = allocatedQuotaCount;
    }

    public int getAllocatedQuotaCount() {
        return allocatedQuotaCount;
    }

    public void setAllocatedVoucherCount(long allocatedVoucherCount) {
        this.allocatedVoucherCount = allocatedVoucherCount;
    }

    public long getAllocatedVoucherCount() {
        return allocatedVoucherCount;
    }

    public void setUnAllocatedVoucherCount(long unAllocatedVoucherCount) {
        this.unAllocatedVoucherCount = unAllocatedVoucherCount;
    }

    public long getUnAllocatedVoucherCount() {
        return unAllocatedVoucherCount;
    }

    public List<ClainQuotaPojo> getClainQuotas() {
        return clainQuotas;
    }

    public void setClainQuotas(List<ClainQuotaPojo> clainQuotas) {
        this.clainQuotas = clainQuotas;
    }


    private Redemptions redemptions;

    public Redemptions getRedemptions() {
        return redemptions;
    }

    public void setRedemptions(Redemptions redemptions) {
        this.redemptions = redemptions;
    }

    public static class Redemptions {

        private long total_redeemed;
        private long total_failed;
        private long total_succeeded;
        private long total_rolled_back;
        private long total_rollback_failed;
        private long total_rollback_succeeded;

        public long getTotal_redeemed() {
            return total_redeemed;
        }

        public void setTotal_redeemed(long total_redeemed) {
            this.total_redeemed = total_redeemed;
        }

        public long getTotal_failed() {
            return total_failed;
        }

        public void setTotal_failed(long total_failed) {
            this.total_failed = total_failed;
        }

        public long getTotal_succeeded() {
            return total_succeeded;
        }

        public void setTotal_succeeded(long total_succeeded) {
            this.total_succeeded = total_succeeded;
        }

        public long getTotal_rolled_back() {
            return total_rolled_back;
        }

        public void setTotal_rolled_back(long total_rolled_back) {
            this.total_rolled_back = total_rolled_back;
        }

        public long getTotal_rollback_failed() {
            return total_rollback_failed;
        }

        public void setTotal_rollback_failed(long total_rollback_failed) {
            this.total_rollback_failed = total_rollback_failed;
        }

        public long getTotal_rollback_succeeded() {
            return total_rollback_succeeded;
        }

        public void setTotal_rollback_succeeded(long total_rollback_succeeded) {
            this.total_rollback_succeeded = total_rollback_succeeded;
        }
    }

}
