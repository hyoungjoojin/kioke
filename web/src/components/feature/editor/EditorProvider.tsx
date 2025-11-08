'use client';

import type { BlockOperation } from '@/types/page';
import { createContext, useContext, useEffect, useRef } from 'react';

interface EditorContextType {
  sendBlockOperations: (ops: BlockOperation[]) => void;
}

const EditorContext = createContext<EditorContextType | null>(null);

const useEditorContext = () => {
  const context = useContext(EditorContext);
  if (!context) {
    throw new Error();
  }

  return context;
};

interface EditorProviderProps {
  children: React.ReactNode;
}

function EditorProvider({ children }: EditorProviderProps) {
  const workerRef = useRef<Worker | null>(null);

  useEffect(() => {
    if (workerRef.current === null) {
      workerRef.current = new Worker(
        new URL('@/workers/editor', import.meta.url),
      );
    }

    workerRef.current.addEventListener('message', handleEditorWorkerMessage);

    return () => {
      workerRef.current?.removeEventListener(
        'message',
        handleEditorWorkerMessage,
      );
    };
  }, []);

  const handleEditorWorkerMessage = (event: MessageEvent<any>) => {
    const data = event.data;
    console.log(data);
  };

  return (
    <EditorContext.Provider
      value={{
        sendBlockOperations(message) {
          workerRef.current?.postMessage(message);
        },
      }}
    >
      {children}
    </EditorContext.Provider>
  );
}

export default EditorProvider;
export { useEditorContext };
