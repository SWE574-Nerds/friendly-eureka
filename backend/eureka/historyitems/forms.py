from django import forms

# class PictureWidget(forms.widgets.Widget):
#     def render(self, name, value, attrs=None):
#         html =  Template("""<img src="$link"/>""")
#         return mark_safe(html.substitute(link=value))
#
# class UserProfileForm(forms.ModelForm):
#     photo = ImageField(widget=PictureWidget)