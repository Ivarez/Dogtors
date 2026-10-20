import { Routes } from '@angular/router';
import { LandingComponent } from './pages/landing/landing.component';
import { MascotaListComponent } from './pages/mascota-list/mascota-list.component';
import { MascotaFormComponent } from './pages/mascota-form/mascota-form.component';
import { MascotaDetailComponent } from './pages/mascota-detail/mascota-detail.component';
import { DuenoListComponent } from './pages/dueno-list/dueno-list.component';
import { DuenoFormComponent } from './pages/dueno-form/dueno-form.component';
import { DuenoDetailComponent } from './pages/dueno-detail/dueno-detail.component';
import { LoginComponent } from './pages/login/login.component';
import { PerfilDuenoComponent } from './pages/perfil-dueno/perfil-dueno.component';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'login', component: LoginComponent },
  { path: 'perfil/:id', component: PerfilDuenoComponent },
  { path: 'mascotas', component: MascotaListComponent },
  { path: 'mascotas/nueva', component: MascotaFormComponent },
  { path: 'mascotas/editar/:id', component: MascotaFormComponent },
  { path: 'mascotas/:id', component: MascotaDetailComponent },
  { path: 'duenos', component: DuenoListComponent },
  { path: 'duenos/nuevo', component: DuenoFormComponent },
  { path: 'duenos/editar/:id', component: DuenoFormComponent },
  { path: 'duenos/:id', component: DuenoDetailComponent },
  { path: '**', redirectTo: '' }
];
