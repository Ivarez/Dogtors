// Mobile drawer toggle and small interactive behaviors
(function(){
  const hamburger = document.getElementById('hamburger');
  const drawer = document.getElementById('mobileDrawer');
  if(hamburger && drawer){
    hamburger.addEventListener('click', function(){
      const open = drawer.getAttribute('aria-hidden') === 'true';
      drawer.style.display = open ? 'flex' : 'none';
      drawer.setAttribute('aria-hidden', String(!open));
      hamburger.setAttribute('aria-expanded', String(open));
    });

    // close drawer on link click
    drawer.querySelectorAll('a').forEach(a=>a.addEventListener('click', ()=>{
      drawer.style.display = 'none';
      drawer.setAttribute('aria-hidden','true');
      hamburger.setAttribute('aria-expanded','false');
    }));
  }

  // Set current year
  const yearEl = document.getElementById('year');
  if(yearEl) yearEl.textContent = new Date().getFullYear();

  // Simple hover color transitions for links that should change color
  document.querySelectorAll('.nav-desktop a, .muted-link, .link-list a').forEach(el=>{
    el.addEventListener('mouseenter', ()=> el.style.color = getComputedStyle(document.documentElement).getPropertyValue('--dark') || '#264653');
    el.addEventListener('mouseleave', ()=> el.style.color = '');
  });
})();
