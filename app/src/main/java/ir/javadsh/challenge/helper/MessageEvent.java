package ir.javadsh.challenge.helper;

import ir.javadsh.challenge.db.model.ReportLog;

public class MessageEvent {

    public ReportLog reportLog;

    public MessageEvent(ReportLog reportLog) {
        this.reportLog = reportLog;
    }

    public ReportLog getMessage() {
        return this.reportLog;
    }

}
