import I18nProvider from './I18nProvider';
import QueryProvider from './QueryProvider';
import { StoreProvider } from './StoreProvider';
import EditorProvider from '@/components/feature/editor/EditorProvider';

export default function RootProvider({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <I18nProvider>
      <QueryProvider>
        <StoreProvider>
          <EditorProvider>{children}</EditorProvider>
        </StoreProvider>
      </QueryProvider>
    </I18nProvider>
  );
}
