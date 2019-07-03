package com.cogitech.lependu.Activities.asynctask;

import android.os.AsyncTask;

import com.cogitech.lependu.Activities.Models.Terminal;

public class GetTerminalAT extends AsyncTask {
    private Exception exception;
    private Terminal terminal;
    private Terminal tt;
    private GetTerminalATResult getTerminalATResult ;


    public GetTerminalAT(GetTerminalATResult getTerminalATResult , Terminal t ) {
        this.getTerminalATResult = getTerminalATResult;
        this.tt = t ;
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        try{
           terminal = WSUtils.getTerminal(tt);
        } catch (Exception e) {
            exception =e ;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(getTerminalATResult != null){
            if (exception != null){
                getTerminalATResult.getTerminalResultErreur(exception);
            }else{
                getTerminalATResult.terminalCharges(terminal);
            }
        }


    }

    public interface GetTerminalATResult {
        void terminalCharges(Terminal terminal) ;
        void getTerminalResultErreur(Exception exception);
    }
}
