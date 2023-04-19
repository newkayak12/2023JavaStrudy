package designPattern;

import java.awt.*;
import java.awt.event.*;


    /**
     * 중재자를 통해 처리한다.
     *
     * 10명이 모여 공동작업을 하는데 잡음이 생긴다. 이때 중재자를 둔다. 중재자는 멤머의 의견을 듣고 지시를 내린다.
     * 마찬가지로 문제가 생기거나 그룹 전체로 파급시킬 일이 있으면 중재자에 알리고, 중재가 지시한 대로 일을 처리한다.
     * 그룹의 각 멤버가 마음대로 다른 멤버와 소통하고 판단하는 것이 아니라, 항상 중재를 통해서 행동한다.
     * 중재자는 그룹 멤버로부터 올라오는 보고를 바탕으로 판단 후 멤버에 할당한다.
     *
     *
     * Mediator에서 중재자는 mediator, colleague로 나뉜다.
     */

    interface Mediator {
        public void createColleagues();
        public void colleagueChanged();
    }
    interface  Colleague {
        public void setMediator(Mediator mediator);
        public void setColleagueEnabled(boolean enabled);
    }
    class ColleagueButton extends Button implements Colleague {
        private Mediator mediator;

        public ColleagueButton(String label){
            super(label);
        }


        @Override
        public void setMediator(Mediator mediator) {
            mediator = mediator;
        }

        @Override
        public void setColleagueEnabled(boolean enabled) {
            setEnabled(enabled);
        }
    }
    class ColleagueTextField extends TextField implements TextListener, Colleague {
        private Mediator mediator;

        public ColleagueTextField(String text, int columns) throws HeadlessException {
            super(text, columns);
        }

        @Override
        public void setMediator(Mediator mediator) {
            this.mediator = mediator;
        }

        @Override
        public void setColleagueEnabled(boolean enabled) {
            setEnabled(enabled);
            setBackground(enabled ? Color.white : Color.lightGray);
        }

        @Override
        public void textValueChanged(TextEvent e) {
            mediator.colleagueChanged();
        }
    }
    class ColleagueCheckbox extends Checkbox implements ItemListener, Colleague {
        private Mediator mediator;
        public ColleagueCheckbox(String label, CheckboxGroup group, boolean state) throws HeadlessException {
            super(label, group, state);
        }

        @Override
        public void setMediator(Mediator mediator) {
            this.mediator = mediator;
        }

        @Override
        public void setColleagueEnabled(boolean enabled) {
            setEnabled(enabled);
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            mediator.colleagueChanged();
        }
    }

    class LoginFrame extends Frame implements ActionListener, Mediator {
        private ColleagueCheckbox checkGuest;
        private ColleagueCheckbox checkLogin;
        private ColleagueTextField textUser;
        private ColleagueTextField textPass;
        private ColleagueButton buttonOk;
        private ColleagueButton buttonCancel;

        public LoginFrame(String title) throws HeadlessException {
            super(title);
            setBackground(Color.lightGray);
            setLayout(new GridLayout(4,2));
            createColleagues();

            add(checkGuest);
            add(checkLogin);
            add(new Label("UserName"));
            add(textUser);
            add(new Label("Password"));
            add(textPass);
            add(buttonOk);
            add(buttonCancel);

            colleagueChanged();

            pack();
            setVisible(true);
        }

        @Override
        public void createColleagues() {
            CheckboxGroup group = new CheckboxGroup();
            checkGuest = new ColleagueCheckbox("Guest", group, true);
            checkLogin = new ColleagueCheckbox("Login", group, false);

            textUser = new ColleagueTextField("", 10);
            textPass = new ColleagueTextField("", 10);
            textPass.setEchoChar('*');

            buttonOk = new ColleagueButton("OK");
            buttonCancel = new ColleagueButton("Cancel");

            checkGuest.setMediator(this);
            checkLogin.setMediator(this);
            textUser.setMediator(this);
            textPass.setMediator(this);
            buttonOk.setMediator(this);
            buttonCancel.setMediator(this);


            checkGuest.addItemListener(checkGuest);
            checkLogin.addItemListener(checkLogin);
            textUser.addTextListener(textUser);
            textPass.addTextListener(textPass);
            buttonOk.addActionListener(this);
            buttonOk.addActionListener(this);

        }

        @Override
        public void colleagueChanged() {
            if(checkGuest.getState()) {
                textUser.setColleagueEnabled(false);
                textPass.setColleagueEnabled(false);
                buttonOk.setColleagueEnabled(false);
            } else {
                textUser.setColleagueEnabled(true);
                userPasswordChanged();
            }

        }
        private void userPasswordChanged() {
            if(textUser.getText().length() > 0){
                textUser.setColleagueEnabled(true);

                if( textPass.getText().length() > 0 ) {
                    buttonOk.setColleagueEnabled(true);
                } else {
                    buttonOk.setColleagueEnabled(false);
                }

            } else {

                textPass.setColleagueEnabled(false);
                buttonOk.setEnabled(false);

            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.toString());
            System.exit(0);
        }
    }

public class C16_Mediator {
    public static void main(String[] args) {
        new LoginFrame("Mediator Sample");
    }
}

/**
 * Mediator : Colleague와 통신하고 통신하고 조정하는 인터페이스를 정의한다.
 * ConcreteMediator : Mediator의 인터페이스를 구현해 실제로 조정한다.
 * Colleague : Mediator와 통신하는 인터페이스를 정의
 * ConcreteColleague : Colleague의 인터페이스를 구현다.
 */
