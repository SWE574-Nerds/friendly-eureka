from django import forms
from .models import Post, Comment, Category, TimeInfo
from ckeditor.fields import RichTextField


class PostForm(forms.ModelForm):
    title = forms.CharField(max_length=100, label='', widget=forms.TextInput(attrs={'placeholder': 'Title', 'class': 'form-control'}))
    category = forms.ModelChoiceField(queryset=Category.objects.all(), widget=forms.Select(attrs={'class':'form-control'}))
    content = RichTextField()
    timeInfo = forms.ModelChoiceField(queryset=TimeInfo.objects.all(), widget=forms.Select(attrs={'class':'form-control'}))
    timeValue1 = forms.CharField(max_length=50, label='', widget=forms.TextInput(attrs={'placeholder': 'Value 1', 'class': 'form-control'}))
    timeValue2 = forms.CharField(max_length=50, label='', widget=forms.TextInput(attrs={'placeholder': 'Value 2', 'class': 'form-control'}))

    class Meta:
        model = Post
        fields = [
            'title',
            'category',
            'content',
            'timeInfo',
            'timeValue1',
            'timeValue2',
        ]


class CommentForm(forms.ModelForm):

    class Meta:
        model = Comment
        fields = [
            'name',
            'content',
        ]
