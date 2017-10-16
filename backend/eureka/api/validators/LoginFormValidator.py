class LoginFormValidator(object):

    def validate(self, login_form):
        reasons = []

        if login_form.name is None:
            reasons.append("Name can't be Null")
        if login_form.password is None:
            reasons.append("Password can't be Null")

        return reasons
