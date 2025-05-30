'use client';

import Theme from '@/constants/theme';
import { ThemeProvider as NextThemesProvider } from 'next-themes';

export function ThemeProvider({
  children,
  ...props
}: React.ComponentProps<typeof NextThemesProvider>) {
  return (
    <NextThemesProvider
      attribute='class'
      enableSystem
      disableTransitionOnChange
      defaultTheme='system'
      themes={Object.values(Theme)}
      {...props}
    >
      {children}
    </NextThemesProvider>
  );
}
