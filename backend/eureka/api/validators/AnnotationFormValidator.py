class AnnotationFormValidator(object):

    def validate(self, annotation_form):
        reasons = []

        if annotation_form.body is None:
            reasons.append("body can't be Null")
        if annotation_form.listory is None:
            reasons.append("listory id can't be Null")

        return reasons
