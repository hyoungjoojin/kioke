import { format } from 'date-fns';
import pino from 'pino';

const logger = pino({
  level: process.env.NODE_ENV === 'development' ? 'debug' : 'warn',
  browser: {
    write: (o) => {
      const { msg, time } = o as Record<string, string>;

      const formattedTime = format(new Date(time), `HH:mm:ss.sss`);

      if (process.env.NODE_ENV === 'development') {
        // eslint-disable-next-line no-console
        console.log(`[${formattedTime}] ${msg}`);
      }
    },
  },
});

export default logger;
