// Dogtors — Landing Page Interactions
(function(){
  // Mobile drawer toggle
  const hamburger = document.getElementById('hamburger');
  const drawer = document.getElementById('mobileDrawer');
  if(hamburger && drawer){
    hamburger.addEventListener('click', function(){
      const isHidden = drawer.getAttribute('aria-hidden') === 'true';
      drawer.style.display = isHidden ? 'flex' : 'none';
      drawer.setAttribute('aria-hidden', String(!isHidden));
      hamburger.setAttribute('aria-expanded', String(isHidden));
    });

    // Close drawer on link click
    drawer.querySelectorAll('a').forEach(function(a){
      a.addEventListener('click', function(){
        drawer.style.display = 'none';
        drawer.setAttribute('aria-hidden', 'true');
        hamburger.setAttribute('aria-expanded', 'false');
      });
    });
  }

  // Set current year in footer
  var yearEl = document.getElementById('year');
  if(yearEl) yearEl.textContent = new Date().getFullYear();
})();
