import { Component, Input } from '@angular/core';
import { Card } from './card';


@Component({
  selector: 'my-test',
  templateUrl: './test.component.html',
  styleUrls: [ './test.component.css' ],
})



export class TestComponent {


  cards: Card[] = [
    {image: "https://static.birgun.net/resim/haber-ici-resim/2016/09/09/tombili-nin-heykelini-yaptigim-icin-kendimi-cok-sansli-hissediyorum-184378-1.jpg", title: "Modadaki tombili heykeli", owner: "catLover1923", lastUpdate: "8 months ago", description: "Tembelliği ve umarsamızlığıyla dünyaca ünlü çizgi film kahramanı Garfield'e benzeyen Tombili, Kadıköy Ziverbey Mahallesi'ndeki bir ..."},
    {image: "", title: "Modadaki tombili heykeli", owner: "catLover1923", lastUpdate: "8 months ago", description: "Tembelliği ve umarsamızlığıyla dünyaca ünlü çizgi film kahramanı Garfield'e benzeyen Tombili, Kadıköy Ziverbey Mahallesi'ndeki bir ..."},
    {image: "", title: "Modadaki tombili heykeli", owner: "catLover1923", lastUpdate: "8 months ago", description: "Tembelliği ve umarsamızlığıyla dünyaca ünlü çizgi film kahramanı Garfield'e benzeyen Tombili, Kadıköy Ziverbey Mahallesi'ndeki bir ..."},
    {image: "", title: "Modadaki tombili heykeli", owner: "catLover1923", lastUpdate: "8 months ago", description: "Tembelliği ve umarsamızlığıyla dünyaca ünlü çizgi film kahramanı Garfield'e benzeyen Tombili, Kadıköy Ziverbey Mahallesi'ndeki bir ..."},
    {image: "", title: "Modadaki tombili heykeli", owner: "catLover1923", lastUpdate: "8 months ago", description: "Tembelliği ve umarsamızlığıyla dünyaca ünlü çizgi film kahramanı Garfield'e benzeyen Tombili, Kadıköy Ziverbey Mahallesi'ndeki bir ..."},
  ];
}
