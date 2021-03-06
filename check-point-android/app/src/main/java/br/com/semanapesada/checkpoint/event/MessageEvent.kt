package br.com.semanapesada.checkpoint.event

import br.com.semanapesada.checkpoint.database.CheckPoint

/**
 * Created by Alfredo L. Porfirio on 01/10/18.
 * Copyright Universo Online 2018. All rights reserved.
 */
class MessageEvent {
    class NewCheckPoint(val checkPoint: CheckPoint)
    class ChangeDistance(val meters: Float)
}
