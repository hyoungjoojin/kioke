'use client';

import Theme, { getThemeDisplayName } from '@/constants/theme';
import {
  usePreferencesActions,
  usePreferredTheme,
} from '@/hooks/store/preferences';
import { cn } from '@/lib/utils';

export default function AppearanceTab() {
  const preferredTheme = usePreferredTheme();
  const { setTheme } = usePreferencesActions();

  return (
    <>
      <section>
        <div className='mb-3'>
          <h1 className='text-xl'>Theme Preferences</h1>
        </div>

        <div className='grid grid-cols-3 gap-4'>
          {Object.values(Theme).map((theme) => {
            return (
              <div
                className={cn(
                  theme,
                  'border shadow rounded-lg',
                  'h-20',
                  'bg-background text-foreground',
                  'hover:cursor-pointer',
                )}
                key={theme}
                onClick={() => {
                  setTheme(theme);
                }}
              >
                {theme === preferredTheme && <span>V</span>}
                {getThemeDisplayName(theme)}
              </div>
            );
          })}
        </div>
      </section>
    </>
  );
}
