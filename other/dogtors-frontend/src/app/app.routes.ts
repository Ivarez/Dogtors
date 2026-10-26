import { inject } from '@angular/core';
import { CanActivateFn, Router, Routes } from '@angular/router';
import { LandingComponent } from './pages/landing/landing.component';
import { MascotaListComponent } from './pages/mascota-list/mascota-list.component';
import { MascotaFormComponent } from './pages/mascota-form/mascota-form.component';
import { MascotaDetailComponent } from './pages/mascota-detail/mascota-detail.component';
import { DuenoListComponent } from './pages/dueno-list/dueno-list.component';
import { DuenoFormComponent } from './pages/dueno-form/dueno-form.component';
import { DuenoDetailComponent } from './pages/dueno-detail/dueno-detail.component';
import { LoginComponent } from './pages/login/login.component';
import { PerfilDuenoComponent } from './pages/perfil-dueno/perfil-dueno.component';
import { MedicamentosExcelComponent } from './pages/medicamentos-excel/medicamentos-excel.component';
import { TratamientoFormComponent } from './pages/tratamiento-form/tratamiento-form.component';
import { ErrorPageComponent } from './pages/error-page/error-page.component';
import { AuthService } from './services/auth.service';

/**
 * Restringe el acceso a rutas exclusivas del veterinario (CRUD de mascotas, CRUD de dueños,
 * asignación de tratamientos y catálogo de medicamentos) si el usuario en sesión es un cliente.
 */
export const restringirClienteGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.esDueno()) {
    const idDueno = authService.usuarioActual()?.id;
    return idDueno ? router.createUrlTree(['/perfil', idDueno]) : router.createUrlTree(['/']);
  }
  return true;
};

export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'login', component: LoginComponent },
  { path: 'perfil/:id', component: PerfilDuenoComponent },
  { path: 'mascotas', component: MascotaListComponent, canActivate: [restringirClienteGuard] },
  { path: 'mascotas/nueva', component: MascotaFormComponent, canActivate: [restringirClienteGuard] },
  { path: 'mascotas/editar/:id', component: MascotaFormComponent, canActivate: [restringirClienteGuard] },
  { path: 'mascotas/:id', component: MascotaDetailComponent },
  { path: 'duenos', component: DuenoListComponent, canActivate: [restringirClienteGuard] },
  { path: 'duenos/nuevo', component: DuenoFormComponent, canActivate: [restringirClienteGuard] },
  { path: 'duenos/editar/:id', component: DuenoFormComponent, canActivate: [restringirClienteGuard] },
  { path: 'duenos/:id', component: DuenoDetailComponent, canActivate: [restringirClienteGuard] },
  { path: 'medicamentos', component: MedicamentosExcelComponent, canActivate: [restringirClienteGuard] },
  { path: 'drogas', redirectTo: 'medicamentos' },
  { path: 'tratamientos', component: TratamientoFormComponent, canActivate: [restringirClienteGuard] },
  { path: 'tratamientos/nuevo', component: TratamientoFormComponent, canActivate: [restringirClienteGuard] },
  { path: 'error', component: ErrorPageComponent },
  { path: '**', redirectTo: '' }
];
