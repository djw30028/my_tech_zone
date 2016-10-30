// Import component decorator
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Pet } from '../pet';
import { PetService } from '../pets.service';

@Component({
  templateUrl: '/app/pigs/pig-list.component.html'
})

 
// Component class
export class PigListComponent implements OnInit {
  pigs: Pet[];

  constructor(private petService: PetService) {}
  
  ngOnInit() {
    // Pass retreived pets to the property
    this.petService.getPigs().then(thePets => this.pigs = thePets);;
  }
}