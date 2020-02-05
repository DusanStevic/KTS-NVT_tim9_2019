import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  public placemarkProperties = {
    hintContent: 'Current location',
    balloonContent: 'Current location'

  };

  public placemarkOptions = {
    iconLayout: 'default#image',
    iconImageHref: 'https://cdn0.iconfinder.com/data/icons/small-n-flat/24/678111-map-marker-512.png',
    iconImageSize: [32, 32]
  };

  constructor(public dialogRef: MatDialogRef<MapComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {latitude: number, longitude: number}) { }

  ngOnInit() {

  }

}
