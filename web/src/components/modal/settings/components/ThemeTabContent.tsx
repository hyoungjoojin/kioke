import { SettingsEntry } from '.';
import { useSettings } from './SettingsContext';
import { Theme } from '@/constant/theme';
import { cn } from '@/lib/utils';
import { useMyProfileQuery } from '@/query/profile';
import { useEffect, useState } from 'react';

function ThemeTabContent() {
  const { updateSettings } = useSettings();

  const { data: profile } = useMyProfileQuery();
  const [selectedTheme, setSelectedTheme] = useState<Theme | null>(null);

  useEffect(() => {
    if (selectedTheme !== null) {
      return;
    }

    if (profile) {
      setSelectedTheme(profile.theme);
    }
  }, [profile, selectedTheme]);

  if (!profile) {
    return null;
  }

  return (
    <>
      <SettingsEntry title='Theme'>
        <div className='grid grid-cols-3 gap-4'>
          {Object.keys(Theme).map((theme, index) => {
            const t = Theme[theme as keyof typeof Theme];

            return (
              <ThemePreview
                onClick={() => {
                  setSelectedTheme(t);
                  updateSettings((settings) => ({
                    ...settings,
                    theme: t,
                  }));
                }}
                selected={theme === selectedTheme}
                theme={t}
                key={index}
              />
            );
          })}
        </div>
      </SettingsEntry>
    </>
  );
}

function ThemePreview({
  theme,
  selected,
  onClick,
}: {
  theme: Theme;
  selected: boolean;
  onClick: () => void;
}) {
  return (
    <div className='flex justify-center items-center'>
      <div
        onClick={onClick}
        className={cn(
          'h-18 w-32',
          'flex justify-center items-center',
          selected && 'bg-red-500',
        )}
      >
        {theme}
      </div>
    </div>
  );
}

export default ThemeTabContent;
