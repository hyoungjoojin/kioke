import I18nProvider from './I18nProvider';
import QueryProvider from './QueryProvider';
import { StoreProvider } from './StoreProvider';
import { TransactionProvider } from './TransactionProvider';

export default function RootProvider({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <I18nProvider>
      <QueryProvider>
        <StoreProvider>
          <TransactionProvider>{children}</TransactionProvider>
        </StoreProvider>
      </QueryProvider>
    </I18nProvider>
  );
}
