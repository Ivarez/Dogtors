import { Component } from '@angular/core';
import { HeroSectionComponent } from './components/hero-section/hero-section.component';
import { StatsSectionComponent } from './components/stats-section/stats-section.component';
import { WhyUsSectionComponent } from './components/why-us-section/why-us-section.component';
import { ProcessSectionComponent } from './components/process-section/process-section.component';
import { FooterSectionComponent } from './components/footer-section/footer-section.component';

@Component({
  selector: 'app-landing',
  imports: [
    HeroSectionComponent,
    StatsSectionComponent,
    WhyUsSectionComponent,
    ProcessSectionComponent,
    FooterSectionComponent
  ],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.scss'
})
export class LandingComponent { }
