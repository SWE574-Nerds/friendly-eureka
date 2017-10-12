class RegisterFormValidator(object):

    def validate(self, register_form):
        reasons = []

        if register_form.mail is None:
            reasons.append("Mail can't be Null")
        if register_form.name is None:
            reasons.append("Name can't be Null")
        if register_form.password is None:
            reasons.append("Password can't be Null")
        else:
            if len(register_form.password) < 8:
                reasons.append("Password can't be smaller than 8 characters")

        return reasons
