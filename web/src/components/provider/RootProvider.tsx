import I18nProvider from './I18nProvider';
import QueryProvider from './QueryProvider';
import { StoreProvider } from './StoreProvider';

export default function RootProvider({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <I18nProvider>
      <QueryProvider>
        <StoreProvider>{children}</StoreProvider>
      </QueryProvider>
    </I18nProvider>
  );
}
