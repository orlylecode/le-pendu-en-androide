package com.cogitech.lependu.Activities.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Terminal implements Parcelable {
        private  String phone;
        private String password;
        private int marge;
        private int essai;
        private double montant;
        private double montantAG;
        private String codeJeu ;

        public Terminal(String phone, String password, int marge, int essai, double montant, double montantAG, String codeJeu) {
                this.phone = phone;
                this.password = password;
                this.marge = marge;
                this.essai = essai;
                this.montant = montant;
                this.montantAG = montantAG;
                this.codeJeu = codeJeu;
        }

        protected Terminal(Parcel in) {
                phone = in.readString();
                password = in.readString();
                marge = in.readInt();
                essai = in.readInt();
                montant = in.readDouble();
                montantAG = in.readDouble();
                codeJeu = in.readString();
        }

        public static final Creator<Terminal> CREATOR = new Creator<Terminal>() {
                @Override
                public Terminal createFromParcel(Parcel in) {
                        return new Terminal(in);
                }

                @Override
                public Terminal[] newArray(int size) {
                        return new Terminal[size];
                }
        };

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public int getMarge() {
                return marge;
        }

        public void setMarge(int marge) {
                this.marge = marge;
        }

        public int getEssai() {
                return essai;
        }

        public void setEssai(int essai) {
                this.essai = essai;
        }

        public double getMontant() {
                return montant;
        }

        public void setMontant(double montant) {
                this.montant = montant;
        }

        public double getMontantAG() {
                return montantAG;
        }

        public void setMontantAG(double montantAG) {
                this.montantAG = montantAG;
        }

        public String getCodeJeu() {
                return codeJeu;
        }

        public void setCodeJeu(String codeJeu) {
                this.codeJeu = codeJeu;
        }

        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(phone);
                dest.writeString(password);
                dest.writeInt(marge);
                dest.writeInt(essai);
                dest.writeDouble(montant);
                dest.writeDouble(montantAG);
                dest.writeString(codeJeu);
        }
}
