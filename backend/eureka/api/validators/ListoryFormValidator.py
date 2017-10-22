class ListoryFormValidator(object):

    def validate(self, listory_form):
        reasons = []

        if listory_form.name is None:
            reasons.append("name can't be Null")
        if listory_form.description is None:
            reasons.append("description can't be Null")

        return reasons
